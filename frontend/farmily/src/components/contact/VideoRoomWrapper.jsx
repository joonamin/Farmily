import React from 'react';
import { useParams } from 'react-router-dom';
import VideoRoomComponent from './VideoRoomComponent'; // 경로는 실제 구조에 맞게 조정하세요.

function VideoRoomWrapper() {
  const { familyId } = useParams()
  return <VideoRoomComponent familyId={familyId} />;
}

export default VideoRoomWrapper;