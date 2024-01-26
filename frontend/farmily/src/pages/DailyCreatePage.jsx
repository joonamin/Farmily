import { useState, useEffect } from 'react';
import CreateDetail from '../components/CreateDetail';
import SmallButton from '../components/SmallButton.jsx';

export default function DailyCreatePage() {
  return (
    <>
      <h1>일상 글쓰기</h1>
      <CreateDetail />
    </>
  );
}
