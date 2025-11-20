SUMMARY = "This is a collection of custom blocks that are not directly associated with a project. "
HOMEPAGE = "https://github.com/M17-Project/gr-m17"
LICENSE = "GPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=b234ee4d69f5fce4486a80fdaf4a4263"

RDEPENDS:${PN} = "gnuradio python3-click"

inherit gnuradio-oot

PV = "0.0.4+git${SRCPV}"

SRC_URI = "gitsm://github.com/M17-Project/gr-m17;branch=dev;protocol=https \
          "
S = "${WORKDIR}/git"

SRCREV = "${AUTOREV}"
