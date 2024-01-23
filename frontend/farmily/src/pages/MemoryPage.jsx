import { useState, useEffect } from 'react'

import MemoryItem from '../components/MemoryItem.jsx'

// 테스트 데이터 // sprint 데이터 받아오면 수정
const TESTITEMS = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15]

export default function MemoryPage() {
  return (
    <div className="h-full overflow-hidden p-5">
      <h1>추억보기 페이지</h1>
      <div className="h-full flex flex-wrap justify-center snap-y overflow-y-scroll">

      {/* sprint 정보 보내기 */}
      {TESTITEMS.map((item, index) => (
        <MemoryItem key={index} />
        ))}
      </div>
    </div>
  )
}