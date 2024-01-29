import { useState, useEffect } from 'react';
import CreateDetail from '../components/common/CreateDetail.jsx';
import SmallButton from '../components/button/SmallButton.jsx';

export default function DailyCreatePage() {
  return (
    <>
      <h1>일상 글쓰기</h1>
      <CreateDetail />
    </>
  );
}
