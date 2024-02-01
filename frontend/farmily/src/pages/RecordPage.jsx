import { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import ArticleList from '../components/common/ArticleList.jsx';
import ImageList from '../components/common/ImageList.jsx';
import SmallButton from '../components/button/SmallButton.jsx';

export default function RecordPage() {
  const { sprintId } = useParams();
  return (
    <>
      <h1>통계 자리</h1>
      <ImageList />
      <ArticleList sprintId={sprintId} />
      {/* 현재 진행중 sprintId와 같은 경우에만 보이게 수정하기 */}
      <div className="flex justify-end top-0 right-0">
        <SmallButton text="글쓰기" url="/family/record/create" />
      </div>
    </>
  );
}
