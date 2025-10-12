DESCRIPTION = "LinHT Share Files - Amateur Radio Handheld Transceiver Resources"
HOMEPAGE = "https://github.com/OE3ANC/LinHT-share"
LICENSE = "CLOSED"
LIC_FILES_CHKSUM = ""

# Use git fetcher to clone the repository
SRC_URI = "git://github.com/M17-Project/LinHT-share.git;protocol=https;branch=main"
SRCREV = "${AUTOREV}"

# Set version based on git revision
PV = "1.0+git${SRCPV}"

# Source directory is the git checkout
S = "${WORKDIR}/git"

# Install task to copy linht folder contents
do_install() {
    # Create the destination directory
    install -d ${D}${datadir}/linht
    
    # Check if linht directory exists and copy contents
    if [ -d "${S}/linht" ]; then
        # Copy all files and subdirectories from linht folder
        cp -r ${S}/linht/* ${D}${datadir}/linht/
        
        # Set appropriate permissions for files
        find ${D}${datadir}/linht -type f -exec chmod 644 {} \;
        find ${D}${datadir}/linht -type d -exec chmod 755 {} \;
    else
        bbwarn "linht directory not found in repository, copying entire repository contents"
        # Fallback: copy entire repository if linht folder doesn't exist
        cp -r ${S}/* ${D}${datadir}/linht/
        find ${D}${datadir}/linht -type f -exec chmod 644 {} \;
        find ${D}${datadir}/linht -type d -exec chmod 755 {} \;
    fi
}

# Package the installed files
FILES:${PN} = "${datadir}/linht"

# Remove the problematic PACKAGE_ARCH line
