#!/bin/bash
find /mnt/app/huangniao/huangniaoapp/logs -mtime +10  -name "*.out.*" -exec rm -rf {} \;