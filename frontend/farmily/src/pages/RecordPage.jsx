import { useState, useEffect } from 'react';
import ArticleList from '../components/common/ArticleList.jsx';
import ImageList from '../components/common/ImageList.jsx';

export default function RecordPage() {
  return (
    <>
      <h1>통계 자리</h1>
      <ImageList />
      <ArticleList />
    </>
  );
}
