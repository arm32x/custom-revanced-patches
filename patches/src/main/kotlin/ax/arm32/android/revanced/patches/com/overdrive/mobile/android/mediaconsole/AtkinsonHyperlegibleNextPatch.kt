package ax.arm32.android.revanced.patches.com.overdrive.mobile.android.mediaconsole

import app.revanced.patcher.patch.PatchException
import app.revanced.patcher.patch.resourcePatch
import ax.arm32.android.revanced.classLoader
import ax.arm32.android.revanced.findElementByAttributeValue
import java.io.File
import java.nio.file.Files
import java.nio.file.StandardCopyOption

@Suppress("unused")
val atkinsonHyperlegibleNextPatch = resourcePatch(
    name = "Replace OpenDyslexic with Atkinson Hyperlegible Next",
    description = "Replace OpenDyslexic with Atkinson Hyperlegible Next in the font style menu when reading an ebook.",
) {
    compatibleWith("com.overdrive.mobile.android.mediaconsole"("3.11.0"))

    apply {
        // Replace font files
        // TODO: Real italics, somehow... The original app doesn't support those.
        classLoader.getResourceAsStream("atkinson-hyperlegible-next/AtkinsonHyperlegibleNext-Regular.otf")!!.use {
            Files.copy(it, get("assets/fonts/opendyslexic.otf").toPath(), StandardCopyOption.REPLACE_EXISTING)
        }
        classLoader.getResourceAsStream("atkinson-hyperlegible-next/AtkinsonHyperlegibleNext-Bold.otf")!!.use {
            Files.copy(it, get("assets/fonts/opendyslexicbold.otf").toPath(), StandardCopyOption.REPLACE_EXISTING)
        }

        // Replace "OpenDyslexic" text in menus
        // ---------------------------------------------------------------------
        // BEGIN copied from ReVanced Patches, with changes
        // https://gitlab.com/ReVanced/revanced-patches/-/blob/015fe10a23ea5b919a3fda6e7da2c176517ae75d/patches/src/main/kotlin/app/revanced/patches/protonmail/signature/RemoveSentFromSignaturePatch.kt

        val stringResourceFiles = mutableListOf<File>()
        get("res").walk().forEach { file ->
            if (file.isFile && file.name.equals("strings.xml", ignoreCase = true)) {
                stringResourceFiles.add(file)
            }
        }

        var foundString = false
        stringResourceFiles.forEach { filePath ->
            document(filePath.absolutePath).use { document ->
                var node = document.documentElement.childNodes.findElementByAttributeValue(
                    "name",
                    "epub_fontfamily_opendyslexic",
                )

                // String is not localized in all languages.
                if (node != null) {
                    node.textContent = "Atkinson Hyperlegible Next"
                    foundString = true
                }
            }
        }

        if (!foundString) {
            throw PatchException("Could not find 'OpenDyslexic' string in resources")
        }

        // END copied from ReVanced Patches, with changes
        // ---------------------------------------------------------------------
    }
}
