# python3-xyzservices_2025.4.0.bb
SUMMARY = "Repository of XYZ raster-tile providers"
HOMEPAGE = "https://github.com/geopandas/xyzservices"
LICENSE  = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=6a3e440ffacb99f21fa410467c048574"

PV       = "2025.4.0"
SRC_URI  = "https://files.pythonhosted.org/packages/source/x/xyzservices/xyzservices-${PV}.tar.gz"
SRC_URI[sha256sum] = "6fe764713648fac53450fbc61a3c366cb6ae5335a1b2ae0c3796b495de3709d8"

# Standard helpers for a pure-Python package
inherit pypi setuptools3
# Add setuptools_scm to avoid the original error
DEPENDS += "python3-setuptools-scm-native"
# No compiled code → nothing to strip or split; no extra RDEPENDS needed
RDEPENDS:${PN} += "python3-core"

do_compile:prepend() {
    # Create pip symlink if it doesn't exist
    if [ ! -f ${STAGING_BINDIR_NATIVE}/pip ]; then
        ln -sf ${STAGING_BINDIR_NATIVE}/pip3 ${STAGING_BINDIR_NATIVE}/pip
    fi
}


# Include the data files in the package
FILES:${PN} += "${datadir}/xyzservices/*"