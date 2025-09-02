do_install:append() {
    # Create python -> python3 symlink
    ln -sf python3 ${D}${bindir}/python
}

# Package the python symlink
FILES:${PN} += "/usr/bin/python"