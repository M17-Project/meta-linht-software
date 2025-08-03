
SUMMARY = "Fourier-based neural vocoder for high-quality audio synthesis"
HOMEPAGE = "https://github.com/charactr-platform/vocos"
AUTHOR = "Hubert Siuzdak <huberts@charactr.com>"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=ed1301f9f3e0d4d3af4db44165fc8545"

SRC_URI = "git://github.com/gemelo-ai/vocos.git;protocol=https;branch=main"
SRCREV = "c859e3b7b534f3776a357983029d34170ddd6fc3"

S = "${WORKDIR}/git"

RDEPENDS_${PN} = "python3-torch python3-torchaudio python3-numpy python3-scipy python3-einops python3-pyyaml python-huggingface3-hub python3-encodec"

inherit setuptools3
