do_install:append() {
    # Create pip -> pip3 symlink  
    ln -sf pip3 ${D}${bindir}/pip
}

# Package the pip symlink
FILES:${PN} += "/usr/bin/pip"
