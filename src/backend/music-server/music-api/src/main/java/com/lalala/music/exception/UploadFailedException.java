package com.lalala.music.exception;

public class UploadFailedException extends BusinessException {
    public UploadFailedException() {
        super(ErrorCode.MUSIC_UPLOAD_FAILED);
    }
}
