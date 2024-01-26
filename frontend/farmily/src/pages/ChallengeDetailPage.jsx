import { useState, useEffect } from 'react';

import ArticleDetail from '../components/ArticleDetail.jsx';
import Comment from '../components/Comment.jsx';

export default function ChallengeDetailPage() {
  return (
    <div className="overflow-y-auto max-h-full">
      <ArticleDetail title="제목" content="엄청긴내용" />
      <Comment />
    </div>
  );
}
