import { useState, useEffect } from 'react';

import CreateDetail from '../components/CreateDetail.jsx';
import SmallButton from '../components/SmallButton.jsx';

export default function ChallengeCreatePage() {
  return (
    <>
      <h1>챌린지 글쓰기</h1>
      <div className=" h-2/4">
        <CreateDetail />
      </div>
      <div className="h-2/5">
        <label htmlFor="startDate">시작 날짜 : </label>
        <input
          type="date"
          id="startDate"
          className="mr-32 border border-stone-700 rounded"
        />
        <label htmlFor="endDate">종료 날짜 : </label>
        <input
          type="date"
          id="endDate"
          className="border border-stone-700 rounded"
        />
      </div>
      <div>
        <SmallButton text="글쓰기" url="/family/record" />
      </div>
    </>
  );
}
