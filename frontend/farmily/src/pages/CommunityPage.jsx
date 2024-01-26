import { useState, useEffect } from 'react'

import CommunityItem from '../components/CommunityItem.jsx';
import SmallButton from '../components/SmallButton.jsx';

const TESTITEMS = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15]

export default function CommunityPage() {
  return (
    <div className="h-full overflow-hidden p-5">
      <div className=" h-5/6 flex flex-wrap justify-center snap-y overflow-y-scroll">

      {/* sprint 정보 보내기 */}
      {TESTITEMS.map((item, index) => (
        <CommunityItem key={index} />
        ))}
      </div>
      <div className="flex justify-end pr-4 pt-4">
        <SmallButton text='글쓰기' url='/family/community/write' />
      </div>
    </div>
  )
}