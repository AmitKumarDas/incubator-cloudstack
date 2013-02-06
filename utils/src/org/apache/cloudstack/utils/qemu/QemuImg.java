// Licensed to the Apache Software Foundation (ASF) under one
// or more contributor license agreements.  See the NOTICE file
// distributed with this work for additional information
// regarding copyright ownership.  The ASF licenses this file
// to you under the Apache License, Version 2.0 (the
// "License"); you may not use this file except in compliance
// the License.  You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing,
// software distributed under the License is distributed on an
// "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
// KIND, either express or implied.  See the License for the
// specific language governing permissions and limitations
// under the License.
package org.apache.cloudstack.utils.qemu;

import org.apache.cloudstack.utils.qemu.QemuImgFile;

import com.cloud.utils.script.Script;

import java.util.List;
import java.util.Map;

public class QemuImg {

    /* The qemu-img binary. We expect this to be in $PATH */
    public String _qemuImgPath = "qemu-img";

    /* Shouldn't we have KVMPhysicalDisk and LibvirtVMDef read this? */
    public static enum PhysicalDiskFormat {
        RAW("raw"), QCOW2("qcow2"), VMDK("vmdk"), FILE("file"), RBD("rbd"), SHEEPDOG("sheepdog"), HTTP("http"), HTTPS("https");
        String format;

        private PhysicalDiskFormat(String format) {
            this.format = format;
        }

        public String toString() {
            return this.format;
        }
    }

    public QemuImg() {

    }

    public QemuImg(String qemuImgPath) {
        this._qemuImgPath = qemuImgPath;
    }

    /* These are all methods supported by the qemu-img tool */

    /* Perform a consistency check on the disk image */
    public void check(QemuImgFile file) {

    }

    /* Create a new disk image */
    public void create(QemuImgFile file, List<Map<String, String>> options) {
        Script s = new Script(_qemuImgPath);
        s.add("create");
        s.add("-f");
        s.add(file.getFormat().toString());
        s.add(file.getFileName());
        s.add(Long.toString(file.getSize()));
    }

    public void create(QemuImgFile file) {
        this.create(file, null);
    }

    /* Convert the disk image filename or a snapshot snapshot_name to disk image output_filename using format output_fmt. */
    public void convert(QemuImgFile srcFile, QemuImgFile destFile, List<Map<String, String>> options) {

    }

    public void convert(QemuImgFile srcFile, QemuImgFile destFile) {
        this.convert(srcFile, destFile, null);
    }

    /* Commit the changes recorded in filename in its base image */
    public void commit(QemuImgFile file) {

    }

    /* Give information about the disk image */
    public void info(QemuImgFile file) {

    }

    /* List, apply, create or delete snapshots in image */
    public void snapshot() {

    }

    /* Changes the backing file of an image */
    public void rebase() {

    }

    /* Resize a disk image */
    public void resize(String filename, long size) {
        String newSize = null;
        if (size > 0) {
            newSize = "+" + size;
        } else {
            newSize = "-" + size;
        }
    }
}