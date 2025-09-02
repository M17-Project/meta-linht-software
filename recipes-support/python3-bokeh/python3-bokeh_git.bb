SUMMARY  = "Interactive visualization library for Python"
HOMEPAGE = "https://bokeh.org/"
LICENSE  = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=4d2241e774601133cb2c58ae1277f9a5"

PV       = "3.5.1"
SRC_URI  = "https://files.pythonhosted.org/packages/source/b/bokeh/bokeh-${PV}.tar.gz"
SRC_URI[sha256sum] = "21dbe86842b24d83c73a1aef2de346a1a7c11c437015d6e9d180a1637e6e8197"

inherit pypi setuptools3

RDEPENDS:${PN} = "\
    python3-jinja2 python3-numpy python3-packaging python3-pillow \
    python3-pyyaml python3-tornado python3-typing-extensions python3-xyzservices \
"

do_install:append() {
    SITEDIR=${D}${PYTHON_SITEPACKAGES_DIR}/bokeh

    # 1.  Hard-code the real version so Bokeh.__version__ is correct
    sed -i 's/__version__ = .*/__version__ = "3.5.1"/' ${SITEDIR}/__init__.py

    # 2.  Copy the real SRI JSON to the placeholder name 0.0.0.json
    SRI_DIR=${SITEDIR}/_sri
    if [ -f ${SRI_DIR}/3.5.1.json ] && [ ! -f ${SRI_DIR}/0.0.0.json ]; then
        cp ${SRI_DIR}/3.5.1.json  ${SRI_DIR}/0.0.0.json
    fi
}

FILES:${PN} += "${PYTHON_SITEPACKAGES_DIR}/bokeh/_sri/0.0.0.json"
