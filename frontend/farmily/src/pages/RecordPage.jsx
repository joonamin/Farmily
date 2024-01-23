import { useState, useEffect } from 'react'
import ArticleList from '../components/ArticleList.jsx'
import ImageList from '../components/ImageList.jsx'

export default function RecordPage() {
  return (
    <>
      <h1>기록 페이지</h1>
      <ImageList />
      <ArticleList />
    </>
  )
}