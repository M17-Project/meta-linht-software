SUMMARY = "This is a collection of custom blocks that are not directly associated with a project. "
HOMEPAGE = "https://github.com/M17-Project/gr-m17"
LICENSE = "GPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=1ebbd3e34237af26da5dc08a4e440464"

RDEPENDS:${PN} = "gnuradio python3-click"

inherit gnuradio-oot

PV = "0.0.4+git${SRCPV}"

SRC_URI = "gitsm://github.com/M17-Project/gr-m17;branch=main;protocol=https \
          "
S = "${WORKDIR}/git"

SRCREV = "${AUTOREV}"
