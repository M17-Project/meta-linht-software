SUMMARY = "M17 T9 text entry implementation"
DESCRIPTION = "T9 predictive text input for M17 amateur radio protocol"
HOMEPAGE = "https://github.com/M17-Project/M17_T9"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=b234ee4d69f5fce4486a80fdaf4a4263"

SRCREV = "${AUTOREV}"
PV = "1.0+git${SRCPV}"
PR = "r0"

SRC_URI = "git://github.com/M17-Project/M17_T9.git;protocol=https;branch=dev"

S = "${WORKDIR}/git"

do_compile() {
    ${CC} ${CFLAGS} ${LDFLAGS} -I. -o m17-t9 *.c
}

do_install() {
    install -d ${D}${bindir}
    install -m 0755 m17-t9 ${D}${bindir}/
}

FILES:${PN} = "${bindir}/m17-t9"
