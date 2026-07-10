package ax.arm32.android.revanced

import app.revanced.patcher.patch.PatchException
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList

internal val classLoader = object {}.javaClass.classLoader

// -----------------------------------------------------------------------------
// BEGIN copied from ReVanced Patches
// https://gitlab.com/ReVanced/revanced-patches/-/blob/015fe10a23ea5b919a3fda6e7da2c176517ae75d/patches/src/main/kotlin/app/revanced/util/ResourceUtils.kt#L165-L187

internal fun NodeList.findElementByAttributeValue(attributeName: String, value: String): Element? {
    for (i in 0 until length) {
        val node = item(i)
        if (node.nodeType == Node.ELEMENT_NODE) {
            val element = node as Element

            if (element.getAttribute(attributeName) == value) {
                return element
            }

            // Recursively search.
            val found = element.childNodes.findElementByAttributeValue(attributeName, value)
            if (found != null) {
                return found
            }
        }
    }

    return null
}

internal fun NodeList.findElementByAttributeValueOrThrow(attributeName: String, value: String) =
    findElementByAttributeValue(attributeName, value) ?: throw PatchException("Could not find: $attributeName $value")

// END copied from ReVanced Patches
// -----------------------------------------------------------------------------