#!/bin/sh
# Enable terminal colors
export TERM=xterm-256color
export FORCE_COLOR=1

# Colorize ls output
alias ls='ls --color=auto'
alias ll='ls -la --color=auto'
alias grep='grep --color=auto'

# Set custom prompt with colors
export PS1='\[\033[01;32m\]\u@\h\[\033[00m\]:\[\033[01;34m\]\w\[\033[00m\]\$ '
