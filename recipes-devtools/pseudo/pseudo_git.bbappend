# Backport pseudo 1.9.11 from upstream scarthgap (poky 50fa0e541d).
# Ubuntu 22.04 tar >= 1.34+dfsg-1ubuntu0.1.22.04.4 (CVE-2025-45582) calls
# openat2 via syscall(), which pseudo 1.9.0 does not handle; every
# do_package then fails with "got *at() syscall for unknown directory".
# Drop this once the poky revision in the manifest includes the update.

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:remove = " \
    file://0001-configure-Prune-PIE-flags.patch \
    file://glibc238.patch \
"

SRCREV = "ba8887e5f1e922f866681ec7dec1a00b602a9328"
PV = "1.9.11"

# Setscene tasks which run under fakeroot must not be executed before
# pseudo-native and *all* its runtime dependencies are available in the
# sysroot.
PSEUDO_SETSCENE_DEPS = ""
PSEUDO_SETSCENE_DEPS:class-native = "sqlite3-native:do_populate_sysroot"
do_populate_sysroot_setscene[depends] += "${PSEUDO_SETSCENE_DEPS}"
