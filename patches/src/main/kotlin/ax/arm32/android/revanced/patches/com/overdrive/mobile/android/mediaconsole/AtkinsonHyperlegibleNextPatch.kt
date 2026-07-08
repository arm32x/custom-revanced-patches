package ax.arm32.android.revanced.patches.com.overdrive.mobile.android.mediaconsole

import app.revanced.patcher.patch.rawResourcePatch
import java.nio.file.Files
import java.nio.file.StandardCopyOption

private val classLoader = object {}.javaClass.classLoader

@Suppress("unused")
val atkinsonHyperlegibleNextPatch = rawResourcePatch(
    name = "Replace OpenDyslexic with Atkinson Hyperlegible Next",
    description = "Replace OpenDyslexic with Atkinson Hyperlegible Next in the font style menu when reading an ebook.",
) {
    compatibleWith("com.overdrive.mobile.android.mediaconsole"("3.11.0"))

    apply {
        // TODO: Real italics, somehow... The original app doesn't support those.
        classLoader.getResourceAsStream("atkinson-hyperlegible-next/AtkinsonHyperlegibleNext-Regular.otf")!!.use {
            Files.copy(it, get("assets/fonts/opendyslexic.otf").toPath(), StandardCopyOption.REPLACE_EXISTING)
        }
        classLoader.getResourceAsStream("atkinson-hyperlegible-next/AtkinsonHyperlegibleNext-Bold.otf")!!.use {
            Files.copy(it, get("assets/fonts/opendyslexicbold.otf").toPath(), StandardCopyOption.REPLACE_EXISTING)
        }
    }
}
