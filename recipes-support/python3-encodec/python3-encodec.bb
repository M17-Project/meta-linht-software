SUMMARY = "High fidelity neural audio codec"
HOMEPAGE = "https://github.com/facebookresearch/encodec"
AUTHOR = "Alexandre Défossez, Jade Copet, Yossi Adi, Gabriel Synnaeve <defossez@fb.com>"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=ac0888d982b292db9e2fa2497d04ab41"

SRC_URI = "https://files.pythonhosted.org/packages/62/59/e47bbd0542d0e6f4ce9983d5eb458a01d4b42c81e5c410cb9e159b1061ae/encodec-0.1.1.tar.gz"
SRC_URI[md5sum] = "3b090c97213e6fcb0f90c24e167ef833"
SRC_URI[sha256sum] = "36dde98ccfe6c51a15576476cadfcb3b35a63507b8b8555abd69889a6fba6772"

S = "${WORKDIR}/encodec-0.1.1"

RDEPENDS_${PN} = ""

inherit setuptools3
