SUMMARY = "M17 T9 text entry implementation"
DESCRIPTION = "T9 predictive text input for M17 amateur radio protocol"
HOMEPAGE = "https://github.com/M17-Project/M17_T9"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=b234ee4d69f5fce4486a80fdaf4a4263"

SRCREV = "${AUTOREV}"
PV = "1.0+git${SRCPV}"
PR = "r0"

SRC_URI = "git://github.com/M17-Project/M17_T9.git;protocol=https;branch=main"

S = "${WORKDIR}/git"

do_compile() {
    # Upstream is a library now (no main()); dictionaries are header-only
    ${CC} ${CFLAGS} ${LDFLAGS} -fPIC -shared -o libt9.so t9.c
}

do_install() {
    install -d ${D}${libdir}
    install -d ${D}${includedir}/t9

    install -m 755 libt9.so ${D}${libdir}/
    install -m 644 t9.h ${D}${includedir}/
    install -m 644 dict/dict_en.h dict/dict_en_sorted.h dict/dict_en_offsets.h ${D}${includedir}/t9/
}

FILES:${PN} = "${libdir}/libt9.so"
FILES:${PN}-dev = "${includedir}/*"

INSANE_SKIP:${PN} = "dev-so"
