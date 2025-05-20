package com.example.tenpo.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientResponse {
  private Integer id;
  private String name;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
