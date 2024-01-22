import { useState, useEffect } from 'react'
import LargeButton from '../components/LargeButton.jsx'
import chat from '../assets/images/chat.png'
import call from '../assets/images/call.png'


export default function ContactPage() {

  return (
    <div className="h-screen text-center align-middle w-full-side py-16 px-24">
      <div className="border-8 border-black bg-white h-full rounded-xl flex justify-around items-center">
        {/* 이 아래로 흰 box 안 코드 입력 */}
        {/* 채팅 / 회의 페이지 완성 후 url 경로 수정 필요 */}
        <LargeButton url="/tree" image={chat} text="채팅하기" />
        <LargeButton  url="/tree" image={call} text="회의하기"/>
      </div>
    </div>
  )
}