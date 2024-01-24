import { useState, useEffect } from 'react'

import CommunityItem from '../components/CommunityItem.jsx';

const TESTITEMS = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15]

export default function CommunityPage() {

  return (
    <div className="h-full overflow-hidden p-5">
      <h1>커뮤니티 페이지</h1>
      <div className="h-full flex flex-wrap justify-center snap-y overflow-y-scroll">

      {/* sprint 정보 보내기 */}
      {TESTITEMS.map((item, index) => (
        <CommunityItem key={index} />
        ))}
      </div>
    </div>
  )
}