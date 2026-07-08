group = "app.revanced"

patches {
    about {
        name = "arm32x's custom ReVanced patches"
        description = "Custom ReVanced patches for a few miscellaneous things"
        source = "git@github.com:arm32x/custom-revanced-patches.git"
        author = "arm32x"
        contact = "arm32x@arm32.ax"
        website = "https://github.com/arm32x/custom-revanced-patches"
        license = "GNU General Public License v3.0"
    }
}

val printVersion by tasks.registering {
    doLast {
        logger.lifecycle("Version ${project.version}")
    }
}
