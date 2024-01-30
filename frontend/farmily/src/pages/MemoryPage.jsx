import { useState, useEffect } from 'react';

import MemoryItem from '../components/common/MemoryItem.jsx';

// 테스트 데이터 // sprint 데이터 받아오면 수정
const TESTITEMS = [
  {
    id: 1,
    range: {
      startDate: '2024-01-29',
      endDate: '2024-01-29',
    },
  },
  {
    id: 2,
    range: {
      startDate: '2024-01-29',
      endDate: '2024-01-29',
    },
  },
  {
    id: 3,
    range: {
      startDate: '2024-01-29',
      endDate: '2024-01-29',
    },
  },
  {
    id: 4,
    range: {
      startDate: '2024-01-29',
      endDate: '2024-01-29',
    },
  },
];

export default function MemoryPage() {
  return (
    <div className="h-full overflow-hidden p-5">
      <h1>추억보기 페이지</h1>
      <div className="h-full flex flex-wrap justify-center snap-y overflow-y-scroll">
        {/* sprint 정보 보내기 */}
        {TESTITEMS.map((item, index) => (
          <MemoryItem key={index} {...item} />
        ))}
      </div>
    </div>
  );
}
