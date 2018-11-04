#!/bin/bash
find /mnt/app/huangniao/huangniaoapp/logs -mtime +15  -name "*.out.*" -exec rm -rf {} \;