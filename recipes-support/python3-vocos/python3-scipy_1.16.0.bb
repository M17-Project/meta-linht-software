
SUMMARY = "Fundamental algorithms for scientific computing in Python"
HOMEPAGE = "None"
AUTHOR = "None <None>"
LICENSE = "GPL-3"
LIC_FILES_CHKSUM = "file://LICENSES_bundled.txt;md5=71c17261dd955a387d4f91f7fb0e006c"

SRC_URI = "https://files.pythonhosted.org/packages/81/18/b06a83f0c5ee8cddbde5e3f3d0bb9b702abfa5136ef6d4620ff67df7eee5/scipy-1.16.0.tar.gz"
SRC_URI[md5sum] = "4fccfec90ae370328e8a5fd2e0498bbd"
SRC_URI[sha256sum] = "b5ef54021e832869c8cfb03bc3bf20366cbcd426e02a58e8a58d7584dfbb8f62"

S = "${WORKDIR}/scipy-1.16.0"

RDEPENDS_${PN} = "python3-numpy"

inherit setuptools3
