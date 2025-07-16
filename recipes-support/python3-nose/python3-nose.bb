SUMMARY = "nose extends unittest to make testing easier"
HOMEPAGE = "http://readthedocs.org/docs/nose/"
AUTHOR = "Jason Pellerin <jpellerin+nose@gmail.com>"
LICENSE = "LGPL-2.1-only"
LIC_FILES_CHKSUM = "file://setup.py;md5=dfab030d5d0276ddf13bbe8dd59d9c06"

SRC_URI = "https://files.pythonhosted.org/packages/58/a5/0dc93c3ec33f4e281849523a5a913fa1eea9a3068acfa754d44d88107a44/nose-1.3.7.tar.gz"
SRC_URI[md5sum] = "4d3ad0ff07b61373d2cefc89c5d0b20b"
SRC_URI[sha256sum] = "f1bffef9cbc82628f6e7d7b40d7e255aefaa1adb6a1b1d26c69a8b79e6208a98"

S = "${WORKDIR}/nose-1.3.7"

RDEPENDS_${PN} = ""

inherit setuptools3

do_install:append() {
    rm -rf ${D}/usr/man
}
