
SUMMARY = "A new flavour of deep learning operations"
HOMEPAGE = "None"
AUTHOR = "Alex Rogozhnikov <None>"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=77840d30e97b27d9777d8b4040f1f598"

SRC_URI = "https://files.pythonhosted.org/packages/e5/81/df4fbe24dff8ba3934af99044188e20a98ed441ad17a274539b74e82e126/einops-0.8.1.tar.gz"
SRC_URI[md5sum] = "36ddae65b48b1449e2bbed058de03fdc"
SRC_URI[sha256sum] = "de5d960a7a761225532e0f1959e5315ebeafc0cd43394732f103ca44b9837e84"

S = "${WORKDIR}/einops-0.8.1"

RDEPENDS_${PN} = ""

inherit setuptools3
