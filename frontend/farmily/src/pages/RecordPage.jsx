import { useState, useEffect } from 'react';
import ArticleList from '../components/ArticleList.jsx';
import ImageList from '../components/ImageList.jsx';

export default function RecordPage() {
  return (
    <>
      <h1>통계 자리</h1>
      <ImageList />
      <ArticleList />
    </>
  );
}
