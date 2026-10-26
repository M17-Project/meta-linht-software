# meta-linht-software

A Yocto Project software layer for the [LinHT Handheld Transceiver](https://github.com/M17-Project/LinHT-hw), providing application software, libraries, and system configuration for digital radio communication and M17 protocol support.

## Overview

This layer provides software components and applications for the LinHT project as part of the [M17 project ecosystem](https://m17project.org/). The layer includes the LinHT utilities and web manager, M17 protocol libraries, GNU Radio modules, SoapySDR support, audio codecs, and a complete LinHT system image with all necessary tools and applications.

**Yocto release:** scarthgap

**Required layers:**
- [meta-linht-sdr](https://github.com/M17-Project/meta-linht-sdr) (GNU Radio and SDR components)
- [meta-linht-hardware](https://github.com/M17-Project/meta-linht-hardware) (`mcm-imx93` machine, kernel and device tree)
- meta-openembedded: meta-oe, meta-python, meta-networking, meta-filesystems
- meta-virtualization (container support used by `linht-web`)

The full build procedure, including the CompuLab/NXP BSP setup and the Docker build container, is described on the M17 wiki: [LinHT Image Build](https://wiki.m17foundation.org/index.php?title=LinHT_Image_Build).

## Software components

### LinHT applications
- **linht-utils**: Utilities for the LinHT handheld transceiver
  - Source: [M17-Project/LinHT-utils](https://github.com/M17-Project/LinHT-utils)
- **linht-soapy**: SoapySDR driver for LinHT (SX1255 over ZMQ)
  - Source: [M17-Project/LinHT-utils](https://github.com/M17-Project/LinHT-utils)
- **linht-web**: Lightweight web-based management interface (terminal, file manager, container management)
  - Source: [OE3ANC/linht-web](https://github.com/OE3ANC/linht-web)
- **linht-share**: Shared resource files for the LinHT
  - Source: [M17-Project/LinHT-share](https://github.com/M17-Project/LinHT-share)
- **linht-environment**: Environment variables and shell aliases
- **networkd-usb0-restart**: systemd service that restarts networkd after boot for `usb0`

### Hardware control libraries
- **liblinht-ctrl**: C library for controlling GPIO pins and PWM channels on the LinHT
  - Source: [M17-Project/liblinht-ctrl](https://github.com/M17-Project/liblinht-ctrl)
  - License: GPL-3.0
- **libsx1255**: C library for controlling the SX1255 radio transceiver
  - Source: [M17-Project/libsx1255](https://github.com/M17-Project/libsx1255)
  - License: GPL-3.0

### M17 protocol stack
- **libm17**: Core M17 protocol library
  - Source: [M17-Project/libm17](https://github.com/M17-Project/libm17)
  - License: GPL-2.0
  - Provides M17 protocol encoding/decoding functions
- **libm17-t9**: T9 predictive text input library (`libt9.so`, headers in `/usr/include/t9.h` and `/usr/include/t9/`)
  - Source: [M17-Project/M17_T9](https://github.com/M17-Project/M17_T9)
  - License: GPL-2.0
- **m17-packet**: M17 packet mode encoder and decoder tools
  - Source: [M17-Project/M17_Implementations](https://github.com/M17-Project/M17_Implementations)
  - License: GPL-2.0
- **gr-m17**: GNU Radio M17 blocks
  - Source: [M17-Project/gr-m17](https://github.com/M17-Project/gr-m17)
  - License: GPL-3.0
  - GNU Radio out-of-tree modules for M17 protocol

### Audio codecs and tools
- **Codec2**: Low-bitrate speech codec
  - Source: [drowe67/codec2](https://github.com/drowe67/codec2)
  - License: LGPL-2.1
  - Optimized for voice communication over radio
  - Includes native tools for codebook generation
- **SoX**: Sound eXchange, command line audio processing
  - Source: [chirlu/sox](https://github.com/chirlu/sox)

### GNU Radio and SDR ecosystem
- **GNU Radio**: Software radio framework (from meta-linht-sdr)
- **gr-m17**: GNU Radio M17 blocks
- **gr-foo**: General purpose GNU Radio blocks (from meta-linht-sdr)
- **gr-bokehgui**: Web based display for GNU Radio applications
- **SoapySDR**, **SoapyRemote**, **SoapyAudio**: Vendor neutral SDR support library, network remoting and audio device plugin
- **libpredict**: Satellite orbit prediction library
  - Source: [sp5wwp/libpredict](https://github.com/sp5wwp/libpredict)

### Graphics
- **raylib 5.5** with **raygui 4.0**: Library for graphical user interfaces

### Python packages
- **python3-encodec**: Neural audio codec
- **python3-vocos**: Fourier-based neural vocoder (with python3-einops, python3-pyyaml, python3-scipy)
- **python3-scikit-commpy**: Digital communication toolkit
- **python3-bokeh**, **python3-xyzservices**: Interactive visualization (used by gr-bokehgui)
- **python3-nose**: Testing framework

### System libraries
- **libosmocore**: Osmocom core library for telecom protocols

## Layer structure

```
meta-linht-software/
├── conf/
│   └── layer.conf                          # Layer configuration
├── recipes-core/
│   ├── base-files/                         # System file customizations
│   ├── images/
│   │   └── linht-image.bb                  # Main LinHT system image
│   ├── linht-environment/                  # Environment variables and aliases
│   ├── networkd-usb0-restart/              # usb0 networkd restart service
│   ├── psplash/                            # Boot splash screen
│   └── systemd/                            # systemd configuration (USB network)
├── recipes-devtools/
│   ├── pseudo/                             # pseudo 1.9.11 backport (see below)
│   ├── python/                             # python3 adjustments
│   └── python3-pip/                        # python3-pip adjustments
├── recipes-extended/
│   └── sudo/                               # sudo configuration
├── recipes-graphics/
│   ├── libsdl2/                            # SDL2 library modifications
│   └── raylib/                             # raylib + raygui
├── recipes-multimedia/
│   └── sox/                                # SoX audio tool
├── recipes-support/
│   ├── codec2/                             # Codec2 speech codec
│   ├── codec2-native/                      # Native codec2 tools
│   ├── gr-bokehgui/                        # GNU Radio web GUI
│   ├── gr-m17/                             # GNU Radio M17 blocks
│   ├── liblinht-ctrl/                      # LinHT GPIO/PWM control library
│   ├── libm17/                             # M17 protocol library
│   ├── libm17-t9/                          # T9 text entry library
│   ├── libosmocore/                        # Osmocom core library
│   ├── libpredict/                         # Satellite orbit prediction
│   ├── libsx1255/                          # SX1255 transceiver library
│   ├── linht-share/                        # LinHT shared resources
│   ├── linht-utils/                        # LinHT utilities and SoapySDR driver
│   ├── linht-web/                          # LinHT web manager
│   ├── m17-packet/                         # M17 packet mode tools
│   ├── overrides/                          # System file overrides
│   ├── python3-*/                          # Python packages
│   └── soapysdr/                           # SoapySDR, SoapyRemote, SoapyAudio
├── COPYING.GPL-v3                          # License file
└── README.md                               # This file
```

## LinHT system image

The [linht-image.bb](recipes-core/images/linht-image.bb) provides a complete system image with:

### Default user
- **User**: `linht`, **password**: `linht`
- Member of the `sudo`, `audio`, `dialout`, `plugdev`, `shutdown` and `docker` groups

### Image features
- **debug-tweaks**: Development and debugging tools
- **tools-profile**: Performance profiling tools
- **tools-sdk**: Software development kit
- **package-management**: Package management system
- **nfs-client**: Network file system client
- **tools-debug**: Debugging utilities
- **ssh-server-openssh**: SSH server for remote access
- **hwcodecs**: Hardware codec support
- **splash**: Boot splash screen

### LinHT software
- **linht-utils**, **linht-web**, **linht-share**, **linht-environment**
- **m17-packet**, **gr-m17**, **libm17**, **libm17-t9**
- **liblinht-ctrl**, **libsx1255**, **linht-soapy**

### Development tools
- **sudo**, **zsh**, **nano**, **git**, **htop**, **pv**: Shell and everyday tools
- **cmake**: Build system
- **perf**: Performance analysis tools
- **dtc**: Device tree compiler
- **kmscube**: KMS/DRM test application
- **evtest**: Input device testing
- **e2fsprogs-resize2fs**, **parted**, **util-linux**: Storage tools

### Audio system
- **alsa-tools**, **alsa-state**, **alsa-utils**, **alsa-lib**: ALSA subsystem
- **ffmpeg**, **flac**, **sox**, **libsndfile1**: Audio processing
- **codec2**: Speech codec

### Hardware interface
- **libgpiod**: GPIO device library
- **gpsd**: GNSS daemon
- **fbida**: Framebuffer image viewer

### Radio and SDR tools
- Complete GNU Radio suite with M17 support (gnuradio, gr-m17, gr-foo)
- SoapySDR with SoapyRemote, SoapyAudio and the LinHT driver
- ZeroMQ for inter-process communication

### Containers
- **packagegroup-container**: Container runtime used by linht-web

Most libraries are also installed with their `-dev` packages so applications can be built directly on the device.

## System configuration

### Network configuration
- **USB networking**: Configured via systemd-networkd
- **SSH access**: OpenSSH server enabled by default

### Audio configuration
- **ALSA**: Complete audio subsystem

### Development environment
- **Cross-compilation**: Full SDK and development tools
- **Debugging**: GDB, perf, and profiling tools
- **Package management**: Runtime package installation

## Integration instructions

### Adding to build configuration

1. Add required layers to `bblayers.conf`:
```bash
BBLAYERS += "/path/to/meta-linht-sdr"
BBLAYERS += "/path/to/meta-linht-hardware"
BBLAYERS += "/path/to/meta-linht-software"
```

2. Build the LinHT image:
```bash
bitbake linht-image
```

## Build notes

### Recipes track upstream HEAD
Most LinHT and M17 recipes use `SRCREV = "${AUTOREV}"` and always build the latest commit of their upstream branch. A change upstream (renamed branches, new build layout) can therefore break the build without any change in this layer. To build a reproducible image, pin `SRCREV` to a specific commit.

### pseudo backport for Ubuntu 22.04 build hosts
Ubuntu 22.04 `tar` from version `1.34+dfsg-1ubuntu0.1.22.04.4` onwards (security fix for CVE-2025-45582) uses the `openat2` system call. The pseudo version shipped with the poky revision of the NXP `imx-6.6.52-2.2.x` manifests (pseudo 1.9.0) cannot handle it, and every `do_package` task fails with:

```
got *at() syscall for unknown directory, fd 4
tar: ./usr/lib: Cannot mkdir: Bad address
```

[recipes-devtools/pseudo/pseudo_git.bbappend](recipes-devtools/pseudo/pseudo_git.bbappend) updates pseudo to 1.9.11, the version used by current upstream scarthgap poky. It can be removed once the BSP manifest moves to a poky revision with pseudo 1.9.10 or newer.

Updating pseudo changes the signature of every fakeroot task, so the first build after this change rebuilds most packages.

## License

This layer is distributed under the GNU General Public License v3.0. See [COPYING.GPL-v3](COPYING.GPL-v3) for details.
