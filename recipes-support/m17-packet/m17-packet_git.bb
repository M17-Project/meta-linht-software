SUMMARY = "M17 packet mode encoder and decoder tools"
DESCRIPTION = "M17 packet mode tools by SP5WWP"
HOMEPAGE = "https://github.com/M17-Project/M17_Implementations"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://../../LICENSE;md5=b234ee4d69f5fce4486a80fdaf4a4263"

SRCREV = "${AUTOREV}"
PV = "1.0+git${SRCPV}"

SRC_URI = "git://github.com/M17-Project/M17_Implementations.git;protocol=https;branch=main"

S = "${WORKDIR}/git/SP5WWP/m17-packet"

DEPENDS = "libm17 libsndfile1"

do_compile() {
    ${CC} ${CFLAGS} ${LDFLAGS} -o m17-packet-encode m17-packet-encode.c -lm -lm17 -lsndfile
    ${CC} ${CFLAGS} ${LDFLAGS} -o m17-packet-decode m17-packet-decode.c -lm -lm17
}

do_install() {
    install -d ${D}${bindir}
    install -m 755 m17-packet-encode ${D}${bindir}/
    install -m 755 m17-packet-decode ${D}${bindir}/
}

FILES:${PN} = "${bindir}/*"
