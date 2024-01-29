import { useState, useEffect } from 'react';
import CommunityItem from '../components/common/CommunityItem.jsx';
import SmallButton from '../components/button/SmallButton.jsx';

const TESTITEMS = [
  { id: 1, title: '게시글 제목1', family: '가족명1' },
  { id: 2, title: '게시글 제목2', family: '가족명2' },
  { id: 3, title: '게시글 제목3', family: '가족명3' },
  { id: 4, title: '게시글 제목4', family: '가족명4' },
  { id: 5, title: '게시글 제목5', family: '가족명5' },
  { id: 6, title: '게시글 제목6', family: '가족명6' },
  { id: 7, title: '게시글 제목7', family: '가족명7' },
  { id: 8, title: '게시글 제목8', family: '가족명8' },
  { id: 9, title: '게시글 제목9', family: '가족명9' },
  { id: 10, title: '게시글 제목', family: '가족명10' },
  { id: 11, title: '게시글 제목', family: '가족명' },
  { id: 12, title: '게시글 제목', family: '가족명' },
  { id: 13, title: '게시글 제목', family: '가족명' },
];

export default function CommunityPage() {
  return (
    <div className="h-full overflow-hidden p-5">
      <div className=" h-5/6 flex flex-wrap justify-center snap-y overflow-y-scroll">
        {/* sprint 정보 보내기 */}
        {TESTITEMS.map((item, index) => (
          <CommunityItem key={index} {...item} />
        ))}
      </div>
      <div className="flex justify-end pr-4 pt-4">
        <SmallButton text="글쓰기" url="/family/community/write" />
      </div>
    </div>
  );
}
