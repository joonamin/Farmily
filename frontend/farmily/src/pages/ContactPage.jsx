import { useState, useEffect } from 'react';
import LargeButton from '../components/button/LargeButton.jsx';
import chat from '../assets/images/chat.png';
import call from '../assets/images/call.png';
import VideoRoomComponent from '../components/contact/VideoRoomComponent.jsx';

export default function ContactPage() {
  return (
    <div className="flex items-center justify-around h-full bg-white rounded-xl">
      <div className="relative w-4/5 h-4/5">
        {' '}
        <VideoRoomComponent style={{ width: '10%', height: '100%' }} />
      </div>
    </div>
  );
}
