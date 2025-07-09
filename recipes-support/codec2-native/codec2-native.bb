DESCRIPTION = "Codec2 from Git"
HOMEPAGE = "https://github.com/drowe67/codec2.git"
LICENSE = "LGPL-2.1"

BBCLASSEXTEND = "native"

SRCREV = "96e8a19c2487fd83bd981ce570f257aef42618f9"
SRC_URI = "git://github.com/drowe67/codec2.git;protocol=https;branch=main"

LIC_FILES_CHKSUM = "file://COPYING;md5=776e198b48f3a1004a437be10854038b"

S = "${WORKDIR}/git"
inherit cmake native

EXTRA_OECMAKE = ""

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${B}/src/codebook*.c ${D}${bindir}
}