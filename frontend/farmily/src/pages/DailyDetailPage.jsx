import { useState, useEffect } from 'react';

import ArticleDetail from '../components/common/ArticleDetail';
import Comment from '../components/common/Comment';

export default function DailyDetailPage() {
  return (
    <div className="overflow-y-auto max-h-full p-10">
      <ArticleDetail title="제목" content="엄청긴내용" />
      <Comment />
    </div>
  );
}
