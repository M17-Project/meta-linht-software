# meta-linht-software

A Yocto Project software layer for the [LinHT Handheld Transceiver](https://github.com/M17-Project/LinHT-hw), providing application software, libraries, and system configuration for digital radio communication and M17 protocol support.

## Overview

This layer provides software components and applications for the LinHT project as part of the [M17 project ecosystem](https://m17project.org/). The layer includes GNU Radio modules, M17 protocol libraries, audio codecs, and a complete LinHT system image with all necessary tools and applications.

**Required Layers:**
- meta-linht-sdr (for GNU Radio and SDR components)

## Software components

### M17 protocol stack
- **libm17**: Core M17 protocol library
  - Source: [M17-Project/libm17](https://github.com/M17-Project/libm17)
  - License: GPL-2.0
  - Provides M17 protocol encoding/decoding functions
- **gr-m17**: GNU Radio M17 blocks
  - Source: [M17-Project/gr-m17](https://github.com/M17-Project/gr-m17)
  - License: GPL-3.0
  - GNU Radio out-of-tree modules for M17 protocol

### Audio codecs
- **Codec2**: Low-bitrate speech codec
  - Source: [drowe67/codec2](https://github.com/drowe67/codec2)
  - License: LGPL-2.1
  - Optimized for voice communication over radio
  - Includes native tools for codebook generation

### GNU Radio ecosystem
- **GNU Radio**: Software radio framework
- **gr-adsb**: ADS-B decoder blocks
- **gr-air-modes**: Aircraft mode decoder
- **gr-ais**: AIS (Automatic Identification System) decoder
- **gr-foo**: General purpose GNU Radio blocks
- **gr-ieee80211**: 802.11 WiFi blocks
- **gr-lora-sdr**: LoRa modulation/demodulation
- **gr-osmosdr**: Hardware abstraction layer
- **gr-pager**: Pager signal decoder

### Python packages
- **python3-encodec**: Neural audio codec
- **python3-vocos**: Voice conversion system
- **python3-scikit-commpy**: Digital communication toolkit
- **python3-nose**: Testing framework
- **Standard packages**: numpy, thrift, click

### System libraries
- **libosmocore**: Osmocom core library for telecom protocols
- **libthrift**: Apache Thrift RPC framework
- **libtalloc**: Memory pool allocator
- **freetype**: Font rendering library

## Layer structure

```
meta-linht-software/
├── conf/
│   └── layer.conf                           # Layer configuration
├── recipes-core/
│   ├── base-files/
│   │   └── base-files_%.bbappend           # System file customizations
│   ├── images/
│   │   └── linht-image.bb                  # Main LinHT system image
│   └── systemd/
│       ├── systemd-conf_%.bbappend         # systemd configuration
│       └── files/
│           └── 20-usb0.network             # USB network configuration
├── recipes-graphics/
│   └── libsdl2/
│       └── libsdl2_%.bbappend              # SDL2 library modifications
├── recipes-support/
│   ├── codec2/                             # Codec2 speech codec
│   ├── codec2-native/                      # Native codec2 tools
│   ├── gr-m17/                             # GNU Radio M17 blocks
│   ├── libm17/                             # M17 protocol library
│   ├── libosmocore/                        # Osmocom core library
│   ├── overrides/                          # System file overrides
│   ├── python3-encodec/                    # Neural audio codec
│   ├── python3-nose/                       # Python testing framework
│   ├── python3-scikit-commpy/              # Digital communication toolkit
│   └── python3-vocos/                      # Voice conversion system
├── COPYING.GPL-v3                          # License file
└── README.md                               # This file
```

## LinHT system image

The [linht-image.bb](recipes-core/images/linht-image.bb) provides a complete system image with:

### Image features
- **debug-tweaks**: Development and debugging tools
- **tools-profile**: Performance profiling tools
- **tools-sdk**: Software development kit
- **package-management**: Package management system
- **nfs-client**: Network file system client
- **tools-debug**: Debugging utilities
- **ssh-server-openssh**: SSH server for remote access
- **hwcodecs**: Hardware codec support

### Development tools
- **nano**: Text editor
- **cmake**: Build system
- **git**: Version control system
- **htop**: System monitor
- **perf**: Performance analysis tools
- **dtc**: Device tree compiler
- **kmscube**: KMS/DRM test application
- **pv**: Pipe viewer for progress monitoring

### Audio system
- **alsa-tools**: ALSA utilities and tools
- **alsa-state**: ALSA state management
- **alsa-utils**: ALSA user-space utilities
- **alsa-lib**: ALSA library

### Hardware interface
- **libgpiod**: GPIO device library
- **libgpiod-dev**: GPIO development headers

### Radio and SDR tools
- **rtl433**: Generic data receiver for ISM band devices
- Complete GNU Radio suite with M17 support
- ZeroMQ for inter-process communication

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
BBLAYERS += "/path/to/meta-linht-software"
```

2. Build the LinHT image:
```bash
bitbake linht-image
```

## Technical notes

### M17 protocol integration
- **libm17**: Provides core M17 protocol functions
- **gr-m17**: GNU Radio blocks for M17 signal processing
- **Integration**: Seamless M17 protocol support in GNU Radio flowgraphs

### Audio processing pipeline
- **Codec2**: Low-bitrate speech encoding for radio transmission
- **ALSA integration**: Complete audio subsystem configuration

### GNU Radio ecosystem
- **Modular design**: Out-of-tree modules for specific protocols
- **Hardware abstraction**: gr-osmosdr for SDR hardware support
- **Protocol support**: Multiple radio protocols (ADS-B, AIS, LoRa, etc.)

### Python integration
- **AI/ML support**: Neural audio codecs (EnCodec, Vocos)
- **Signal processing**: SciPy and NumPy for advanced algorithms
- **Communication**: Thrift RPC for inter-process communication

## License

This layer is distributed under the GNU General Public License v3.0. See [COPYING.GPL-v3](COPYING.GPL-v3) for details.
