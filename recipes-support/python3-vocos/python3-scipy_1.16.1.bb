SUMMARY = "Scientific Library for Python (wheel)"
HOMEPAGE = "https://www.scipy.org"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/BSD-3-Clause;md5=550794465ba0ec5312d6919e203a55f9"

SRC_URI = "https://files.pythonhosted.org/packages/c4/db/8d4afec60eb833a666434d4541a3151eedbf2494ea6d4d468cbe877f00cd/scipy-1.16.1-cp312-cp312-manylinux2014_aarch64.manylinux_2_17_aarch64.whl;downloadfilename=scipy-${PV}.whl"
SRC_URI[sha256sum] = "6c62eea7f607f122069b9bad3f99489ddca1a5173bef8a0c75555d7488b6f725"

DEPENDS = "python3 unzip-native"

# Keep libgfortran in RDEPENDS for proper package management
RDEPENDS:${PN} = "python3-numpy python3-core libgfortran"

inherit python3-dir

do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install() {
    install -d ${D}${PYTHON_SITEPACKAGES_DIR}
    
    cd ${WORKDIR}
    
    if [ -f "scipy-${PV}.whl" ]; then
        unzip -q "scipy-${PV}.whl" -d ${D}${PYTHON_SITEPACKAGES_DIR}/
    else
        WHEEL_FILE=$(find . -name "*cp312*.whl" -type f | head -1)
        if [ -n "$WHEEL_FILE" ]; then
            unzip -q "$WHEEL_FILE" -d ${D}${PYTHON_SITEPACKAGES_DIR}/
        else
            bbfatal "No compatible wheel file found"
        fi
    fi
    
    find ${D}${PYTHON_SITEPACKAGES_DIR} -name "*.dist-info" -type d -exec rm -f {}/WHEEL {}/RECORD \; 2>/dev/null || true
}

FILES:${PN} += "${PYTHON_SITEPACKAGES_DIR}/*"

# Skip QA checks that are problematic with pre-built wheels
INSANE_SKIP:${PN} += "already-stripped arch ldflags file-rdeps"
