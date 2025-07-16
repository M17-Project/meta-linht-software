SUMMARY = "Digital Communication Algorithms with Python"
HOMEPAGE = "http://veeresht.github.com/CommPy"
AUTHOR = " <>"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=cccfd842af1fde7fcfe4517737744a0b"

SRC_URI = "https://files.pythonhosted.org/packages/ad/7b/0adced3f6f3d1082576620c585dff07bcb46fadd9e73b180759c6836f665/scikit-commpy-0.8.0.tar.gz"
SRC_URI[md5sum] = "d89c8eef729359ee4405b1fc9be4d806"
SRC_URI[sha256sum] = "69714e745a2c06881af786933b19116cf69a5533f2e67a8f4f0bad4e6c907834"

S = "${WORKDIR}/scikit-commpy-0.8.0"


DEPENDS += "python3-setuptools-native"
DEPENDS += "python3-numpy"
DEPENDS += "python3-scipy"
DEPENDS += "python3-matplotlib"
DEPENDS += "python3-nose"
DEPENDS += "python3-sympy"

RDEPENDS_${PN} = ""

inherit setuptools3
