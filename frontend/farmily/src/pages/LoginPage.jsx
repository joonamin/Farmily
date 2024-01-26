import React, { useState } from 'react';
import axios from 'axios';

const sites = [
  { name: '카카오', url: 'kakao' },
  { name: '구글', url: 'google' },
  { name: '메타', url: 'facebook' },
];

export default function LoginPage() {
  const [loginStatus, setLoginStatus] = useState('');

  const handleLogin = async (site) => {
    try {
      // 요청 데이터를 설정합니다 (필요에 따라 추가 데이터를 설정)
      const requestData = {
        // 요청 데이터 필드를 여기에 추가
      };

      // POST 요청을 보냅니다.
      const response = await axios.post(`/oauth2/login/code/${site.url}`, requestData);

      // 응답 처리 로직을 추가하세요 (예: 로그인 성공 시 리다이렉트 또는 메시지 표시)
      if (response.status === 200) {
        setLoginStatus(`${site.name} 로그인 성공!`);
      } else {
        setLoginStatus(`${site.name} 로그인 실패`);
      }
    } catch (error) {
      // 오류 처리 로직을 추가하세요 (예: 오류 메시지 표시)
      console.error(`POST 요청 중 오류 발생 (${site.name}):`, error);
      setLoginStatus(`로그인 중 오류 발생 (${site.name})`);
    }
  };

  return (
    <div className="h-screen text-center align-middle w-full py-24 px-60">
      <div className="border-8 border-black bg-white h-full rounded-xl p-10">
        <h1 className="text-2xl mb-5">로그인</h1>
        <div className="h-1/3 flex justify-center">
          {/* 로고 이미지를 추가 */}
          <img src={farmily} alt="Farmily 로고" />
        </div>
        <div className="h-2/3 flex justify-around items-center p-10">
          {sites.map((site, index) => (
            <button
              key={index}
              onClick={() => handleLogin(site)}
              className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded"
            >
              {site.name} 로그인
            </button>
          ))}
        </div>
        {loginStatus && <p>{loginStatus}</p>}
      </div>
    </div>
  );
}
