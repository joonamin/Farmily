import { useState, useEffect } from 'react';

import RecordCreateButton from '../components/button/RecordCreateButton.jsx';

import ChallengeIcon from '../assets/images/ChallengeIcon.png';
import DailyIcon from '../assets/images/DailyIcon.png';
import EventIcon from '../assets/images/EventIcon.png';

export default function ArticleCreatePage() {
  return (
    <>
      <h1>기록 만들기</h1>
      <div className="m-auto px-20 h-full ">
        <RecordCreateButton
          title="이벤트"
          content="사진으로 추억을 기록하세요!"
          img={EventIcon}
          name="event"
        />
        <RecordCreateButton
          title="일상"
          content="잊고 싶지 않은 기억을 남기세요!"
          img={DailyIcon}
          name="daily"
        />
        <RecordCreateButton
          title="챌린지"
          content="우리 가족만의 챌린지를 만들어 보세요!"
          img={ChallengeIcon}
          name="challenge"
        />
      </div>
    </>
  );
}
