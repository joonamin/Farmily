import { useState, useEffect } from 'react';

import ArticleDetail from '../components/ArticleDetail';
import Comment from '../components/Comment';

export default function EventDetailPage() {
  return (
    <div className="overflow-y-auto max-h-full">
      <ArticleDetail title="제목" content="엄청긴내용" />
      <Comment />
    </div>
  );
}
