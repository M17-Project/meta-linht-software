
SUMMARY = "YAML parser and emitter for Python"
HOMEPAGE = "https://pyyaml.org/"
AUTHOR = "Kirill Simonov <xi@resolvent.net>"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=6d8242660a8371add5fe547adf083079"

SRC_URI = "https://files.pythonhosted.org/packages/54/ed/79a089b6be93607fa5cdaedf301d7dfb23af5f25c398d5ead2525b063e17/pyyaml-6.0.2.tar.gz"
SRC_URI[md5sum] = "9600ee49b2b4e1a0237cf4173b6dc594"
SRC_URI[sha256sum] = "d584d9ec91ad65861cc08d42e834324ef890a082e591037abe114850ff7bbc3e"

S = "${WORKDIR}/pyyaml-6.0.2"

RDEPENDS_${PN} = ""

inherit setuptools3
