import { useState, useEffect } from 'react';

import Comment from '../components/Comment.jsx';
import ChallengeCalendar from '../components/ChallengeCalendar.jsx';
import ArticleDetail from '../components/ArticleDetail.jsx';

const startDate = new Date('2024-02-07');
const endDate = new Date('2024-02-15');

export default function ChallengeDetailPage() {
  return (
    <div className="overflow-y-auto max-h-full">
      <ArticleDetail title="제목" content="엄청긴 내용" />
      <div>
        <span className="mr-24">
          시작 기간 : {startDate.toLocaleDateString()}
        </span>
        <span>종료 기간 : {endDate.toLocaleDateString()}</span>
      </div>
      <ChallengeCalendar startDate={startDate} endDate={endDate} />
      <Comment />
    </div>
  );
}
