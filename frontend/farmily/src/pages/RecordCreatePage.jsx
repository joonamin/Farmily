import { useState, useEffect } from 'react';

import RecordCreateButton from '../components/button/RecordCreateButton.jsx';

import ChallengeIcon from '../assets/images/ChallengeIcon.png';
import DailyIcon from '../assets/images/DailyIcon.png';
import EventIcon from '../assets/images/EventIcon.png';

export default function ArticleCreatePage() {
  return (
    <>
      <h1>기록 만들기</h1>
      <RecordCreateButton title="이벤트" img={EventIcon} name="event" />
      <RecordCreateButton title="일상" img={DailyIcon} name="daily" />
      <RecordCreateButton title="챌린지" img={ChallengeIcon} name="challenge" />
    </>
  );
}
