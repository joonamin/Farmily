import { useState, useEffect } from 'react'
import LargeButton from '../components/LargeButton.jsx'
import chat from '../assets/images/chat.png'
import call from '../assets/images/call.png'


export default function ContactPage() {
  return (
    <div className="bg-white h-full rounded-xl flex justify-around items-center">
      {/* 채팅 / 회의 페이지 완성 후 url 경로 수정 필요 */}
      <LargeButton url="/tree" image={chat} text="채팅하기" />
      <LargeButton  url="/tree" image={call} text="회의하기"/>
    </div>
  )
}