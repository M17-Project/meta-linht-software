do_install:append() {
    echo '%sudo ALL=(ALL) ALL' >> ${D}${sysconfdir}/sudoers
}
