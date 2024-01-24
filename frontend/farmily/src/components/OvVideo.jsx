import React, { useEffect, useRef } from 'react';

function OpenViduVideoComponent({ streamManager }) {
    const videoRef = useRef()

    useEffect(() => {
        //streamManager와 videoRef.current가 존재하는 경우
        if (streamManager && videoRef.current) {
            streamManager.addVideoElement(videoRef.current)
        }
    }, [streamManager])

    return <video autoPlay={true} ref={videoRef} width={'1200px'} style={{
        borderRadius: '10px',
        objectFit: 'fill'
    }}/>
}

export default OpenViduVideoComponent;
